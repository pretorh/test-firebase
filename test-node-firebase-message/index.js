const admin = require("firebase-admin");
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

const KEY = getKey();
const serviceAccount = require(KEY.file);
console.log('initialize firebase app');
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: KEY.url
});
