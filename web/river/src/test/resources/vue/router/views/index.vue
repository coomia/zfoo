<template>
    <div>
        <h1>首页</h1>

        <!--有两种跳转页面的方式：1.this.$router.push('/user/123');2.router-link会被渲染出a标签。html5的History模式下会拦截点击，避免浏览器重新加载页面-->
        <!--tag属性可以指定渲染成什么标签，如a标签-->
        <!--replace属性可以让History不会留下任何历史记录，所以后退键不会反悔上一个页面-->
        <!--active-class属性，对应的路由匹配成功时，会自动给当前元素设置一个名为router-link-active的class-->
        <router-link to="/about">跳转到 about</router-link>
    </div>
</template>
<script>
    export default {

    }
</script>