import Mock from 'mockjs';
import { param2Obj } from '@/utils/objUtils.js';

const NameList = [];
const count = 100;

for (let i = 0; i < count; i++) {
    NameList.push(Mock.mock({
        name: '@first'
    }));
}
NameList.push({ name: 'mockPan' });

const searchUser = config => {
    const { name } = param2Obj(config.url);
    const mockNameList = NameList.filter(item => {
        const lowerCaseName = item.name.toLowerCase();
        if (name && lowerCaseName.indexOf(name.toLowerCase()) < 0) return false;
        return true;
    });
    return { items: mockNameList };
};


// 搜索相关
Mock.mock(/\/search\/user/, 'get', searchUser);
