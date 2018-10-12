import mock from 'mockjs';

mock.mock(/\/mock-test\/get/, 'get', (config) => {
    console.log('get-request:' + config.url);
    return {
        mockData: 'get-response'
    };
});

mock.mock(/\/mock-test\/post/, 'post', (config) => {
    console.log('post-request:' + config.body);
    return {
        mockData: 'post-response'
    };
});


export default mock;
