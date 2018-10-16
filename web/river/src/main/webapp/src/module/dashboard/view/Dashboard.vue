<template>
    <div class="dashboard-container">
        <component :is="currentRole"/>
    </div>
</template>

<script>
import { mapGetters } from 'vuex';
import adminDashboard from '@/module/dashboard/component/admin/DashboardAdmin.vue';
import editorDashboard from '@/module/dashboard/component/editor/DashboardEditor.vue';

export default {
    name: 'Dashboard',
    components: { adminDashboard, editorDashboard },
    data() {
        return {
            currentRole: 'adminDashboard'
        };
    },
    computed: {
        ...mapGetters([
            'roles'
        ])
    },
    created() {
        if (!this.roles.includes('admin')) {
            this.currentRole = 'editorDashboard';
        }
    }
};
</script>
