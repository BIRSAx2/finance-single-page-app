import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const Wallet = () => import('@/entities/wallet/wallet.vue');
// prettier-ignore
const WalletUpdate = () => import('@/entities/wallet/wallet-update.vue');
// prettier-ignore
const WalletDetails = () => import('@/entities/wallet/wallet-details.vue');
// prettier-ignore
const Category = () => import('@/entities/category/category.vue');
// prettier-ignore
const CategoryUpdate = () => import('@/entities/category/category-update.vue');
// prettier-ignore
const CategoryDetails = () => import('@/entities/category/category-details.vue');
// prettier-ignore
const Transaction = () => import('@/entities/transaction/transaction.vue');
// prettier-ignore
const TransactionUpdate = () => import('@/entities/transaction/transaction-update.vue');
// prettier-ignore
const TransactionDetails = () => import('@/entities/transaction/transaction-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/wallet',
    name: 'Wallet',
    component: Wallet,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/wallet/new',
    name: 'WalletCreate',
    component: WalletUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/wallet/:walletId/edit',
    name: 'WalletEdit',
    component: WalletUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/wallet/:walletId/view',
    name: 'WalletView',
    component: WalletDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/category',
    name: 'Category',
    component: Category,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/category/new',
    name: 'CategoryCreate',
    component: CategoryUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/category/:categoryId/edit',
    name: 'CategoryEdit',
    component: CategoryUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/category/:categoryId/view',
    name: 'CategoryView',
    component: CategoryDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/transaction',
    name: 'Transaction',
    component: Transaction,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/transaction/new',
    name: 'TransactionCreate',
    component: TransactionUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/transaction/:transactionId/edit',
    name: 'TransactionEdit',
    component: TransactionUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/transaction/:transactionId/view',
    name: 'TransactionView',
    component: TransactionDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
