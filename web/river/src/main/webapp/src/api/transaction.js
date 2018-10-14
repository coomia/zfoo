import request from '@/utils/requestUtils.js';

export function fetchList(query) {
    return request({
        url: '/transaction/list',
        method: 'get',
        params: query
    });
}
