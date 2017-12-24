const admin = require("firebase-admin");
const google = require('googleapis');
const fs = require('fs');
const fetch = require('node-fetch');

function getKey() {
  const m = fs.readdirSync('./')
    .map(f => f.match(/^(.*)-firebase-adminsdk-.*\.json$/))
    .filter(f => f !== null)[0];

  return {
    file: './' + m[0],
    name: m[1],
    url: 'https://' + m[1] + '.firebaseio.com'
  };
}

function getAccessToken(scopes) {
  return new Promise((resolve, reject) => {
    var jwtClient = new google.auth.JWT(serviceAccount.client_email, null, serviceAccount.private_key, scopes, null);

    console.log('authorize...');
    jwtClient.authorize((err, tokens) => {
      if (err) {
        return reject(err);
      }
      resolve(tokens.access_token);
    });
  });
}

function onAuthorized(accessToken) {
  console.log('token: ' + accessToken);

  const url = 'https://fcm.googleapis.com/v1/projects/' + KEY.name + '/messages:send';
  const body = {
    message: {
      notification: {
        title: 'FCM test message',
        body: 'message from test-node-firebase-message',
      },
      token: DEVICE_TOKEN
    }
  };
  const opts = {
    method: 'POST',
    body: JSON.stringify(body),
    headers: {
      'Authorization': 'Bearer ' + accessToken,
      'Content-Type': 'application/json'
    }
  };

  console.log('\nsending message');
  console.log(url);
  console.log(JSON.stringify(opts, null, 2));
  return fetch(url, opts)
    .then(res => res.json());
}

const DEVICE_TOKEN = process.argv[2]
if (!DEVICE_TOKEN) {
  throw new Error('no device token specified (set env var DEVICE_TOKEN)')
}
const KEY = getKey();
const serviceAccount = require(KEY.file);

console.log('initialize firebase app');
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: KEY.url
});

getAccessToken(['https://www.googleapis.com/auth/firebase.messaging'])
  .then(onAuthorized)
  .then(console.log)
  .catch(console.error);
