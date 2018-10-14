<template>
  <article-detail :is-edit="true"/>
</template>

<script>
import ArticleDetail from '@/module/example/component/ArticleDetail.vue';

export default {
    name: 'EditForm',
    components: { ArticleDetail }
};
</script>

