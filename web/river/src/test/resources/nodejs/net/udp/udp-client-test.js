var dgram = require('dgram');

var message = new Buffer('This is client');

var client = dgram.createSocket("udp4");

client.send(message, 0, message.length, 9999, "localhost", function(err, bytes) {
    client.close();
});