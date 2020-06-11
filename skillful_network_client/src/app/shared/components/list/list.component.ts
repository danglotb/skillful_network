import { Component, OnInit, Input, HostListener } from '@angular/core';

import { ListElement } from './list-element';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit {

  @Input() title: string;
  @Input() displayedColumns: string[];
  @Input() listElements: ListElement[];

  dataSource;

  private order: string;
  private keyword = '';
  private pageIndex = 1;
  private field: string;

  public pageSize = 10;
  public pageSizeOptions: number[] = [10, 25, 50, 100];
  public length: number;
  public hidePageSize = false;
  public showFirstLastButtons = true;

  @HostListener('matSortChange', ['$event']) change(event) {
    this.order = event.direction;
    this.field = event.active;
    // this.onSearchByFirstNameOrLastName();
  }

  constructor() { }

  ngOnInit(): void {
    // this.userService.getUsersBySearch(this.keyword, this.page = this.pageIndex, this.size = this.pageSize, this.checkOrder(), this.checkField()).then(res => {
    //   this.length = res.totalElements;
    //   this.dataSource = new MatTableDataSource<User>(res.content);
    // }).finally(() => this.isLoading = false);
  }

}
