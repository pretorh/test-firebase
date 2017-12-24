const admin = require("firebase-admin");
const google = require('googleapis');
const fs = require('fs');

function getKey() {
  const m = fs.readdirSync('./')
    .map(f => f.match(/^(.*)-firebase-adminsdk-.*\.json$/))
    .filter(f => f !== null)[0];

  return {
    file: './' + m[0],
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

function onAuthorized(token) {
  console.log('token: ' + token);
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
  .catch(console.error);
