<template>
    <div>
        <h1>vuex-test</h1>
        <p>{{ count }}</p>
        <button @click="handleIncrement">+1</button>
        <button @click="handleDecrease">-1</button>
        <button @click="handleIncrementMore">+5</button>
        <div>{{ list }}</div>
        <div>{{ listCount }}</div>
        <button @click="handleActionIncrement">action +1</button>
        <button @click="handleAsyncIncrement">async +1</button>
    </div>
</template>

<script>
    export default {
        computed: {
            count() {
                return this.$store.state.count;
            },
            list() {
                // getters在任何组件都能被调用，是一个函数，类似于计算属性
                return this.$store.getters.filteredList;
            },
            listCount() {
                return this.$store.getters.listCount;
            }
        },
        methods: {
            handleIncrement() {
                // commit()函数用来同步执行mutations里的函数
                this.$store.commit('increment');
            },
            handleDecrease() {
                this.$store.commit('decrease');
            },
            handleIncrementMore() {
                this.$store.commit('increment', 5);
            },
            handleActionIncrement() {
                // dispatch()函数用来异步执行actions里的函数，然后在异步函数中会通过context抛到主业务线程中
                this.$store.dispatch('increment')
            },
            handleAsyncIncrement() {
                this.$store.dispatch('asyncIncrement').then(() => {
                    console.log(this.$store.state.count);
                });
            }
        }
    }
</script>

<style scoped>

</style>