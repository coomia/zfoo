###.babelrc文件
webpack会依赖.babelrc配置文件编译ES6代码

###cross-env————使Vue项目如何分环境打包
1.在config/目录下添加test.env.js、pre.env.js。修改prod.env.js里的内容，修改后的内容如下：
```
'use strict'
module.exports = {
  NODE_ENV: '"production"',
  EVN_CONFIG:'"prod"',
  API_ROOT:'"/apis/v1"'
}
```
2.对package.json文件中的scripts内容进行个性，添加上新定义的几种环境的打包过程，里的参数与前面的调协保持一致。
```
 "scripts": {
    "dev": "webpack-dev-server --inline --progress --config build/webpack.dev.conf.js",
    "start": "npm run dev",
    "build": "node build/build.js",
    "build:test": "cross-env NODE_ENV=production env_config=test node build/build.js",
    "build:pre": "cross-env NODE_ENV=production env_config=pre node build/build.js",
    "build:prod": "cross-env NODE_ENV=production env_config=prod node build/build.js"
  }
```
3.在webpackage.prod.conf.js中使用构建环境参数
```
NODE_ENV=production在nodejs中可以用process.env.NODE_ENV=production取得到。
```

