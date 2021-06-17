import { Authority } from '@/shared/security/authority';

const UaaUserManagementComponent = () => import('@/admin/user-management/user-management.vue');
const UaaUserManagementViewComponent = () => import('@/admin/user-management/user-management-view.vue');
const UaaUserManagementEditComponent = () => import('@/admin/user-management/user-management-edit.vue');
const UaaDocsComponent = () => import('@/admin/docs/docs.vue');
const UaaConfigurationComponent = () => import('@/admin/configuration/configuration.vue');
const UaaHealthComponent = () => import('@/admin/health/health.vue');
const UaaLogsComponent = () => import('@/admin/logs/logs.vue');
const UaaMetricsComponent = () => import('@/admin/metrics/metrics.vue');

export default [
  {
    path: '/admin/user-management',
    name: 'UaaUser',
    component: UaaUserManagementComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/user-management/new',
    name: 'UaaUserCreate',
    component: UaaUserManagementEditComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/user-management/:userId/edit',
    name: 'UaaUserEdit',
    component: UaaUserManagementEditComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/user-management/:userId/view',
    name: 'UaaUserView',
    component: UaaUserManagementViewComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/docs',
    name: 'UaaDocsComponent',
    component: UaaDocsComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/health',
    name: 'UaaHealthComponent',
    component: UaaHealthComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/logs',
    name: 'UaaLogsComponent',
    component: UaaLogsComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/metrics',
    name: 'UaaMetricsComponent',
    component: UaaMetricsComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
  {
    path: '/admin/configuration',
    name: 'UaaConfigurationComponent',
    component: UaaConfigurationComponent,
    meta: { authorities: [Authority.ADMIN] },
  },
];
