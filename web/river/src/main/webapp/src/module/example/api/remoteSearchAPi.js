import request from '@/utils/requestUtils.js';

export function userSearch(name) {
    return request({
        url: '/search/user',
        method: 'get',
        params: { name }
    });
}
