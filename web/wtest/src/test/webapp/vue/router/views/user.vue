<template>
    <div>
        <div>{{ $route.params.id }}</div>
        <button @click="handleRouter">跳转到 final</button>
    </div>
</template>
<script>
    export default {
        mounted () {
            console.log(this.$route);
        },

        // 等价于下面语法，当对象的key和value一致时，可以缩写成一个
        // components: {
        //     vTitle: vTitle,
        //     vButton: vButton
        // }

        methods: {
            handleRouter (e) {
                this.$router.push('/user/123/final');
            }
        }
    }
</script>