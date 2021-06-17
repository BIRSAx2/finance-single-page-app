import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICategory } from '@/shared/model/category.model';

import CategoryService from './category.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Category extends Vue {
  @Inject('categoryService') private categoryService: () => CategoryService;
  private removeId: number = null;

  public categories: ICategory[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCategorys();
  }

  public clear(): void {
    this.retrieveAllCategorys();
  }

  public retrieveAllCategorys(): void {
    this.isFetching = true;

    this.categoryService()
      .retrieve()
      .then(
        res => {
          this.categories = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: ICategory): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCategory(): void {
    this.categoryService()
      .delete(this.removeId)
      .then(() => {
        const message = 'A Category is deleted with identifier ' + this.removeId;
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllCategorys();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
