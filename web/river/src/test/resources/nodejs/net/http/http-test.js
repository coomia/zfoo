// Node提供了net，dgram，http，https，这4个模块，分别用于处理TCP、UDP、HTTP、HTTPS
var http = require('http');

var options = {
    host: 'www.google.com',
    port: 80,
    path: '/',
    method: 'POST'
};
var req = http.request(options, function (res) {
    console.log('STATUS: ' + res.statusCode);
    console.log('HEADERS: ' + JSON.stringify(res.headers));
    res.setEncoding('utf8');

    res.on('data', function (chunk) {
        console.log('BODY: \n' + chunk);
    });

    res.on('end', function () {
    });

});

req.on('error', function (e) {
    console.log('problem with request: ' + e.message);
});

// write data to request body
req.write('data\n');
req.write('data\n');
req.end();