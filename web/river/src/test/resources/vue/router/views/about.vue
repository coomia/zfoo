<template>
    <div>
        <h1>介绍页</h1>
        <button @click="handleRouter">跳转到 user</button>
    </div>
</template>
<script>
    export default {
        methods: {
            handleRouter () {
                this.$router.push('/user/123');
                // 它不会向history添加新记录，而是替换掉当前的history记录
                // this.$router.replace('/user/123');
                // this.$router.go(-1); // 后退一页
                // this.$router.go(2); // 前进两页
            }
        }
    }
</script>