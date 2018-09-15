<template>
    <div>
        <v-title title="Vue组件化"></v-title>
        <v-button @click="handleClick">跳回到首页</v-button>
        <p>
            <img src="../../images/image.png" style="width: 200px;">
        </p>
    </div>
</template>
<script>
    import vTitle from './title.vue';
    import vButton from './button.vue';

    export default {
        components: {
            vTitle,
            vButton
        },

        // 等价于下面语法，当对象的key和value一致时，可以缩写成一个
        // components: {
        //     vTitle: vTitle,
        //     vButton: vButton
        // }

        methods: {
            handleClick (e) {
                this.$router.push('/index');
            }
        }
    }
</script>