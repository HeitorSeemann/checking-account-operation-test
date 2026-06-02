import http from 'k6/http';
import { check, sleep } from 'k6';

// 1. TEST CONFIGURATION AND METRIC THRESHOLDS
export const options = {
    // Defines the workload pattern: how many virtual users (VUs) over time
    stages: [
        { duration: '30s', target: 10 }, // Ramp-up: from 0 to 10 users in 30 seconds
        { duration: '1m', target: 10 },  // Plateau: stay at 10 stable users for 1 minute
        { duration: '10s', target: 0 },  // Ramp-down: cool down to 0 users in 10 seconds
    ],
    // Defines global success criteria (SLA). The test fails if thresholds are breached
    thresholds: {
        // 'rate < 0.10' means failure rate must be under 10% (at least 90% must succeed)
        'http_req_failed': ['rate < 0.10'], // Global threshold: 90% of all requests must pass
        'http_req_failed{method:POST}': ['rate < 0.10'], // Specifically 90% of POSTs must pass
        'http_req_failed{method:GET}': ['rate < 0.10'],  // Specifically 90% of GETs must pass
    },
};

// 2. HELPER FUNCTION FOR DYNAMIC DATA GENERATION
// Generates a random integer with exactly 5 positions (between 10000 and 99999)
function getRandom5DigitNumber() {
    return Math.floor(Math.random() * (99999 - 10000 + 1)) + 10000;
}

// 3. MAIN VIRTUAL USER (VU) JOURNEY
export default function () {
    // Generates unique IDs per iteration to prevent cache hitting and data collision
    const randomAccountId = getRandom5DigitNumber();
    const randomIdempotencyKey = getRandom5DigitNumber();

    // Shared base URL containing the dynamic account number
    const baseUrl = `http://localhost:8080/accounts/withdrawals/${randomAccountId}`;

    // ------------------------------------------------------------
    // STEP 1: Execute the Withdrawal Operation (POST)
    // ------------------------------------------------------------
    const postPayload = JSON.stringify({ value: 110 });
    const postParams = {
        headers: {
            'accept': '*/*',
            'Idempotency-Key': randomIdempotencyKey.toString(), // Injecting the 5-digit dynamic header
            'Content-Type': 'application/json',
        },
    };

    const postRes = http.post(baseUrl, postPayload, postParams);

    // Checks evaluate individual requests as true/false (binary checks)
    check(postRes, {
        'POST - Status is 201': (r) => r.status === 201,
        'POST - Response contains notes': (r) => r.body.includes('note'),
    });

    // Pacing: think time (0.5s) to simulate a real user before checking history
    sleep(0.5);

    // ------------------------------------------------------------
    // STEP 2: Consult the Withdrawal History (GET)
    // ------------------------------------------------------------
    const getParams = {
        headers: {
            'accept': '*/*',
        },
    };

    // Requests history using the EXACT same account ID populated in Step 1
    const getRes = http.get(baseUrl, getParams);

    check(getRes, {
        'GET - Status is 200': (r) => r.status === 200,
        'GET - Response contains transaction details': (r) => r.body.includes('id') && r.body.includes('Withdrawal'),
        'GET - Verified correct account data': (r) => r.body.includes(`"account":${randomAccountId}`)
    });

    // Wait 1 second before this Virtual User loops and picks a new random account
    sleep(1);
}
