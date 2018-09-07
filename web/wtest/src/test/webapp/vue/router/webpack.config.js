var path = require('path');
var ExtractTextPlugin = require('extract-text-webpack-plugin');

// webpack必选的两项是Entry（入口）和Output（出口）。入口的作用告诉webpack从哪里开始寻找依赖，并编译，出口则用来配置编译后的文件存储位置和文件名。
var config = {

    // webpack会从main.js开始工作
    entry: {
        main: './main'
    },

    // path用来指定打包后输出的目录
    // publicPath指定资源文件引用的目录
    output: {
        path: path.join(__dirname, './dist'),
        publicPath: '/dist/',
        filename: 'main.js'
    },
    module: {
        rules: [
            {
                test: /\.vue$/,
                loader: 'vue-loader',
                options: {
                    loaders: {
                        css: ExtractTextPlugin.extract({
                            use: 'css-loader',
                            fallback: 'vue-style-loader'
                        })
                    }
                }
            },
            {
                test: /\.js$/,
                loader: 'babel-loader',
                exclude: /node_modules/
            },
            {
                test: /\.css$/,
                use: ExtractTextPlugin.extract({
                    use: 'css-loader',
                    fallback: 'style-loader'
                })
            },

            // 当遇到下面指定的文件时，url会把他们编译到dist目录下，?limit=1024是指如果这个文件大小小于1kb，就以base64的形式加载，不会生成一个文件
            {
                test: /\.(gif|jpg|png|woff|svg|eot|ttf)\??.*$/,
                loader: 'url-loader?limit=1024'
            }
        ]
    },
    plugins: [
        // 重命名提取后的css文件
        new ExtractTextPlugin({
            filename: '[name].css',
            allChunks: true
        })
    ]
};

module.exports = config;