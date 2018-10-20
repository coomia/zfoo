<template>
    <div>
        <button @click="addCookie()">增加cookie：cookieKey -> cookieValue</button>
        <button @click="removeCookie()">移除cookie：cookieKey</button>
    </div>
</template>

<script>
    import cookies from 'js-cookie';

    export default {
        name: 'CookieTest',


        methods: {
            addCookie: function() {
                console.log('addCookie');
                // Default: Cookie is removed when the user closes the browser.
                cookies.set('cookieKey', 'cookieValue');
            },
            removeCookie: function() {
                console.log('removeCookie');
                cookies.remove('cookieKey');
            }
        }

    };
</script>

<style scoped>

</style>
