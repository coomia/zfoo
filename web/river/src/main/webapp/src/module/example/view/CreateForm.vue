<template>
    <article-detail :is-edit="false"/>
</template>

<script>
import ArticleDetail from '@/module/example/component/ArticleDetail.vue';

export default {
    name: 'CreateForm',
    components: { ArticleDetail }
};
</script>

