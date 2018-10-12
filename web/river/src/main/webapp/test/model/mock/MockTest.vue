<template>
    <div>
        <button @click="getMockTest()">get mock test</button>
        <button @click="postMockTest()">post mock test</button>
    </div>
</template>

<script>
    import axios from 'axios';

    export default {
        name: 'MockTest',

        methods: {
            getMockTest: function() {
                axios.get('localhost:9527/mock-test/get-request?paraA=a&paraB=b')
                    .then(function(response) {
                        console.log(response);
                    })
                    .catch(function(error) {
                        console.log(error);
                    });
            },
            postMockTest: function() {
                axios.post('localhost:9527/mock-test/post-request',
                    {
                        firstName: 'jay',
                        lastName: 'sunxiao'
                    })
                    .then(function(response) {
                        console.log(response);
                    })
                    .catch(function(error) {
                        console.log(error);
                    });
            }
        }

    };
</script>

<style scoped>

</style>
