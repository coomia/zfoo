var net = require('net');

var server = net.createServer(function (socket) {

    socket.on('data', function (data) {
        socket.write("新的连接");
    });

    socket.on('end', function () {
        console.log('断开连接');
    });


    socket.on('error', function (e) {
        console.error('服务器发生异常时触发');
    });

    socket.on('close', function () {
       console.log('当服务器关闭时触发');
    });

    socket.write("Hello World\n");

});
server.listen(9999, function () {
    console.log('server bound');
});


