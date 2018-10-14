<template>
  <div class="app-container">
    <switch-roles @change="handleRolesChange" />
  </div>
</template>

<script>
import SwitchRoles from '@/module/permission/component/SwitchRoles';

export default{
    name: 'PagePermission',
    components: { SwitchRoles },
    methods: {
        handleRolesChange() {
            this.$router.push({ path: '/permission/index?' + +new Date() });
        }
    }
};
</script>
