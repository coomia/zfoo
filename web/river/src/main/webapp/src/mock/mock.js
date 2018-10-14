import Mock from 'mockjs';
import '@/module/login/mock/loginMock.js';
import '@/module/article/mock/articleMock.js';
import '@/module/example/mock/remoteSearchMock.js';
import transactionAPI from './transaction';

// Mock.setup({
//   timeout: '350-600'
// })


// 账单相关
Mock.mock(/\/transaction\/list/, 'get', transactionAPI.getList);

export default Mock;
