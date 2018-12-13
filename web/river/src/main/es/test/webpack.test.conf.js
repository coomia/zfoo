'use strict';
const path = require('path');
const { VueLoaderPlugin } = require('vue-loader');
const webpack = require('webpack');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const portfinder = require('portfinder');
const FriendlyErrorsPlugin = require('friendly-errors-webpack-plugin');

function resolve(dir) {
    return path.join(__dirname, '..', dir);
}

const testHost = 'localhost';
const testPort = 9527;


const devWebpackConfig = {
    // context是webpack编译时的基础目录，入口起点（entry）会相对于此目录查找。若不配置，默认值为当前目录。
    context: path.resolve(__dirname, '../'),

    // webpack会从main.js开始工作
    entry: {
        app: './test/main.js'
    },

    // path用来指定打包后输出的目录，最终的打包文件会放到与当前脚本文件同级目录的dist目录下
    // filename用于指定输出文件的名称，[name]对应着入口文件的key值，因为入口文件为main.js，所以key为main
    // publicPath指定资源文件引用的目录。在开发模式用相对路径；在生产模式中，如果资源文件放在别的服务器上，可以使用服务器的地址。
    output: {
        path: path.resolve(__dirname, '../dist'),
        filename: '[name].js',
        publicPath: '/'
    },

    // 在导入语句没带文件后缀时，webpack会自动带上后缀去尝试访问文件是否存在，默认是：extensions:['.js', '.json']
    resolve: {
        extensions: ['.js', '.vue', '.json'],
        alias: {
            '@': resolve('test')
        }
    },

    // loader是在打包前或打包的过程中作用于单个文件。plugin通常在打包过程结束后，作用于包或者chunk级别。
    module: {
        rules: [
            {
                test: /\.(js|vue)$/,
                loader: 'eslint-loader',
                enforce: 'pre', // 提前检查，避免被babel修改过后再检查
                exclude: [resolve('test')], // 指定检查的目录
                options: {
                    formatter: require('eslint-friendly-formatter'), // 指定错误报告的格式规范
                    emitWarning: true
                }
            },
            {
                test: /\.vue$/,
                loader: 'vue-loader',
                options: {
                    // You can set the vue-loader configuration by yourself.
                }
            },

            // loader: babel-loader?cacheDirectory=true，加载器将使用node_modules/.cache/babel-loader中的默认缓存目录来避免编译开销
            {
                test: /\.js$/,
                loader: 'babel-loader?cacheDirectory=true',
                include: [
                    resolve('test'),
                    resolve('node_modules/webpack-dev-server/client')
                ]
            },
            // 自定义的svg只使用svg-sprite-loader处理，element-ui中的svg使用url-loader
            {
                test: /\.svg$/,
                loader: 'svg-sprite-loader',
                include: [resolve('test/common/resource/icon')],
                options: {
                    symbolId: 'icon-[name]'
                }
            },
            // 如果图片多，会发很多http请求，会降低页面性能。
            // url-loader会将引入的图片编码，生成dataURl（将图片数据转换成一串字符打包到文件中），最终只需要引入这个文件就能访问图片了。
            {
                test: /\.(png|jpe?g|gif|svg)(\?.*)?$/,
                loader: 'url-loader',
                exclude: [resolve('test/common/resource/icon')],
                options: {
                    limit: 10000,
                    name: path.join('static', 'img/[name].[hash:7].[ext]')
                }
            },
            {
                test: /\.(mp4|webm|ogg|mp3|wav|flac|aac)(\?.*)?$/,
                loader: 'url-loader',
                options: {
                    limit: 10000,
                    name: path.join('static', 'media/[name].[hash:7].[ext]')
                }
            },
            {
                test: /\.(woff2?|eot|ttf|otf)(\?.*)?$/,
                loader: 'url-loader',
                options: {
                    limit: 10000,
                    name: path.join('static', 'fonts/[name].[hash:7].[ext]')
                }
            },
            {
                test: /\.css$/,
                use: ['vue-style-loader', 'css-loader', 'postcss-loader']
            },
            {
                test: /\.postcss$/,
                use: ['vue-style-loader', 'css-loader', 'postcss-loader']
            },
            {
                test: /\.less$/,
                use: ['vue-style-loader', 'css-loader', 'postcss-loader', 'less-loader']
            },
            {
                test: /\.sass$/,
                use: ['vue-style-loader', 'css-loader', 'postcss-loader', {
                    loader: 'sass-loader',
                    options: {
                        indentedSyntax: true
                    }
                }]
            },
            {
                test: /\.scss$/,
                use: ['vue-style-loader', 'css-loader', 'postcss-loader', 'sass-loader']
            },
            {
                test: /\.stylus$/,
                use: ['vue-style-loader', 'css-loader', 'postcss-loader', 'stylus-loader']
            },
            {
                test: /\.styl$/,
                use: ['vue-style-loader', 'css-loader', 'postcss-loader', 'stylus-loader']
            }
        ]
    },
    plugins: [
        new VueLoaderPlugin(),
        new webpack.HotModuleReplacementPlugin(),
        // DefinePlugin可以将参数传递到前端的页面中，在main.js中可以输出这些值
        new webpack.DefinePlugin({
            'process.env': {
                a: '"a"',
                b: '"b"',
                c: '"c"'
            }
        }),
        // https://github.com/ampedandwired/html-webpack-plugin
        new HtmlWebpackPlugin({
            filename: 'index.html',
            template: 'index.html',
            inject: true,
            favicon: resolve('favicon.png'),
            title: 'river',
            path: '/static'
        })
    ],
    mode: 'development',
    devtool: 'cheap-source-map',
    devServer: {
        clientLogLevel: 'warning',
        historyApiFallback: true,
        hot: true,
        compress: true,
        host: testHost,
        port: testPort,
        open: true,
        overlay: { warnings: false, errors: true },
        publicPath: '/',
        proxy: {},
        quiet: true,
        watchOptions: {
            poll: false
        }
    },
    node: {
        // prevent webpack from injecting useless setImmediate polyfill because Vue
        // source contains it (although only uses it if it's native).
        setImmediate: false,
        // prevent webpack from injecting mocks to Node native modules
        // that does not make sense for the client
        dgram: 'empty',
        fs: 'empty',
        net: 'empty',
        tls: 'empty',
        child_process: 'empty'
    }
};


module.exports = new Promise((resolve, reject) => {
    portfinder.basePort = testPort;
    portfinder.getPort((err, port) => {
        if (err != null) {
            reject(err);
        } else {
            process.env.PORT = port;
            devWebpackConfig.devServer.port = port;
            devWebpackConfig.plugins.push(new FriendlyErrorsPlugin(
                {
                    compilationSuccessInfo: {
                        messages: [`Your application is running here: http://${devWebpackConfig.devServer.host}:${port}`]
                    },
                    onErrors: (severity, errors) => {
                        if (severity !== 'error') return;

                        const error = errors[0];
                        const filename = error.file && error.file.split('!').pop();
                        require('node-notifier').notify({
                            title: 'river-test',
                            message: severity + ': ' + error.name,
                            subtitle: filename || '',
                            icon: path.join(__dirname, 'logo.png')
                        });
                    }
                }
            ));

            resolve(devWebpackConfig);
        }
    });
});

