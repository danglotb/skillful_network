import { Component, OnInit, Input, HostListener, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

import { ListElement } from './list-element';
import { SearchService } from '../../services/abstract-search.service';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit {

  @Input() title: string;
  @Input() displayedColumns: string[];
  @Input() listElements: ListElement[];
  @Input() service: SearchService<any>;

  dataSource;

  private order: string;
  private keyword = '';
  private pageIndex = 1;
  private field: string;
  private page: number;
  private size: number;
  public pageSize = 10;
  public pageSizeOptions: number[] = [10, 25, 50, 100];
  public length: number;
  public hidePageSize = false;
  public showFirstLastButtons = true;

  public isLoading: boolean;

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  search = new FormGroup({
    keyword: new FormControl(''),
  });

  @HostListener('matSortChange', ['$event']) change(event) {
    this.order = event.direction;
    this.field = event.active;
    this.onSearch();
  }
  constructor() { }

  ngOnInit(): void {
    this.isLoading = true;
    this.service.getBySearch(this.keyword, this.page = this.pageIndex, this.size = this.pageSize, this.checkOrder(), this.checkField()).then(res => {
      this.length = res.totalElements;
      this.dataSource = new MatTableDataSource<any>(res.content);
    }).finally(() => this.isLoading = false);
  }

  onSearch() {
    if (event !== undefined) {
      this.pageSize = this.paginator.pageSize;
      this.pageIndex = this.paginator.pageIndex + 1;
    }
    const keyword = this.search.value.keyword;
    this.service.getBySearch(keyword, this.page = this.pageIndex, this.size = this.pageSize, this.checkOrder(), this.checkField())
      .then((res: { totalElements: number; content: any[]; }) => {
        this.length = res.totalElements;
        this.dataSource = new MatTableDataSource<any>(res.content);
      }).finally(() => this.isLoading = false);
  }

  checkField() {
    if (this.field == null) {
      return this.field = this.displayedColumns[0];
    } else {
      return this.field;
    }
  }

  checkOrder() {
    if (this.order == null || this.order === 'asc') {
      this.order = 'ASCENDING';
    } else {
      this.order = 'DESCENDING';
    }
    return this.order;
  }

}
