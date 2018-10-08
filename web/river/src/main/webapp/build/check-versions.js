'use strict';
const chalk = require('chalk');
// 解析npm包的version
const semver = require('semver');
const packageConfig = require('../package.json');
// node版本的uninx shell命令
const shell = require('shelljs');

function exec(cmd) {
    return require('child_process')
        .execSync(cmd)
        .toString()
        .trim();
}

const versionRequirements = [
    {
        name: 'node',
        currentVersion: semver.clean(process.version), // node的版本，process.version就是node的版本
        versionRequirement: packageConfig.engines.node // package.json中定义的node版本的范围
    }
];

// 检查npm的版本
if (shell.which('npm')) {
    versionRequirements.push({
        name: 'npm',
        currentVersion: exec('npm --version'),
        versionRequirement: packageConfig.engines.npm
    });
}

module.exports = function() {
    const warnings = [];

    for (let i = 0; i < versionRequirements.length; i++) {
        const mod = versionRequirements[i];

        if (!semver.satisfies(mod.currentVersion, mod.versionRequirement)) {
            // 如果现有的npm或者node的版本比定义的版本低，则生成一段警告
            warnings.push(
                mod.name +
                ': ' +
                chalk.red(mod.currentVersion) +
                ' should be ' +
                chalk.green(mod.versionRequirement)
            );
        }
    }

    if (warnings.length) {
        console.log('');
        console.log(
            chalk.yellow(
                'To use this template, you must update following to modules:'
            )
        );
        console.log();

        for (let i = 0; i < warnings.length; i++) {
            const warning = warnings[i];
            console.log('  ' + warning);
        }

        console.log();
        process.exit(1); // 退出程序
    }
};
