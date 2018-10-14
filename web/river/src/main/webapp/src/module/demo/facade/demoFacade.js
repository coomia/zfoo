import Layout from '@/module/layout/Layout';

const demoFacade = {
    path: '/demo',
    component: Layout,
    redirect: 'noredirect',
    name: 'Demo',
    meta: {
        title: 'demo',
        icon: 'component'
    },
    children: [
        {
            path: 'tinymce',
            component: () => import('@/module/demo/view/TinymceDemo.vue'),
            name: 'TinymceDemo',
            meta: { title: 'tinymce' }
        },
        {
            path: 'markdown',
            component: () => import('@/module/demo/view/MarkdownDemo.vue'),
            name: 'MarkdownDemo',
            meta: { title: 'markdown' }
        },
        {
            path: 'json-editor',
            component: () => import('@/module/demo/view/JsonEditorDemo.vue'),
            name: 'JsonEditorDemo',
            meta: { title: 'jsonEditor' }
        },
        {
            path: 'splitpane',
            component: () => import('@/module/demo/view/SplitpaneDemo.vue'),
            name: 'SplitpaneDemo',
            meta: { title: 'splitPane' }
        },
        {
            path: 'avatar-upload',
            component: () => import('@/module/demo/view/AvatarUploadDemo.vue'),
            name: 'AvatarUploadDemo',
            meta: { title: 'avatarUpload' }
        },
        {
            path: 'dropzone',
            component: () => import('@/module/demo/view/DropzoneDemo.vue'),
            name: 'DropzoneDemo',
            meta: { title: 'dropzone' }
        },
        {
            path: 'sticky',
            component: () => import('@/module/demo/view/StickyDemo.vue'),
            name: 'StickyDemo',
            meta: { title: 'sticky' }
        },
        {
            path: 'count-to',
            component: () => import('@/module/demo/view/CountToDemo.vue'),
            name: 'CountToDemo',
            meta: { title: 'countTo' }
        },
        {
            path: 'mixin',
            component: () => import('@/module/demo/view/MixinDemo.vue'),
            name: 'ComponentMixinDemo',
            meta: { title: 'componentMixin' }
        },
        {
            path: 'back-to-top',
            component: () => import('@/module/demo/view/BackToTopDemo.vue'),
            name: 'BackToTopDemo',
            meta: { title: 'backToTop' }
        },
        {
            path: 'drag-dialog',
            component: () => import('@/module/demo/view/DragDialogDemo.vue'),
            name: 'DragDialogDemo',
            meta: { title: 'dragDialog' }
        },
        {
            path: 'dnd-list',
            component: () => import('@/module/demo/view/DndListDemo.vue'),
            name: 'DndListDemo',
            meta: { title: 'dndList' }
        },
        {
            path: 'drag-kanban',
            component: () => import('@/module/demo/view/DragKanbanDemo.vue'),
            name: 'DragKanbanDemo',
            meta: { title: 'dragKanban' }
        }
    ]
};

export default demoFacade;
