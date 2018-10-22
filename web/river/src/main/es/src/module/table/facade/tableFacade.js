import Layout from '@/module/layout/view/Layout.vue';

const tableFacade = {
    path: '/table',
    component: Layout,
    redirect: '/table/complex-table',
    name: 'Table',
    meta: {
        title: 'Table',
        icon: 'table'
    },
    children: [
        {
            path: 'dynamic-table',
            component: () => import('@/module/table/view/dynamicTable/DynamicTable.vue'),
            name: 'DynamicTable',
            meta: { title: 'dynamicTable' }
        },
        {
            path: 'drag-table',
            component: () => import('@/module/table/view/DragTable.vue'),
            name: 'DragTable',
            meta: { title: 'dragTable' }
        },
        {
            path: 'inline-edit-table',
            component: () => import('@/module/table/view/InlineEditTable.vue'),
            name: 'InlineEditTable',
            meta: { title: 'inlineEditTable' }
        },
        {
            path: 'tree-table',
            component: () => import('@/module/table/view/treeTable/TreeTableDemo.vue'),
            name: 'TreeTableDemo',
            meta: { title: 'treeTable' }
        },
        {
            path: 'custom-tree-table',
            component: () => import('@/module/table/view/treeTable/CustomTreeTableDemo.vue'),
            name: 'CustomTreeTableDemo',
            meta: { title: 'customTreeTable' }
        },
        {
            path: 'complex-table',
            component: () => import('@/module/table/view/ComplexTable.vue'),
            name: 'ComplexTable',
            meta: { title: 'complexTable' }
        }
    ]
};
export default tableFacade;
