import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PerkService } from 'src/app/shared/services/perk.service';
import { Perk } from '../../models/user/perk';


@Component({
  selector: 'app-chip',
  templateUrl: './chip.component.html',
  styleUrls: ['./chip.component.scss']
})
export class ChipComponent implements OnInit {

  @Input() typePerk: string;
  @Input() title: string;
  @Input() chipValues: Perk[];
  @Input() addingLabel: string;
  @Input() detail: string;

  chipInfoGroup: FormGroup;
  isLoading: boolean
  candidateChips: Perk[];

  constructor(
    private service: PerkService,
    private formBuilder: FormBuilder,
  ) { }

  ngOnInit(): void {
    this.chipInfoGroup = this.formBuilder.group({
      chipValues: this.chipValues,
      chipValue: [null, [Validators.minLength(2), Validators.maxLength(20)]]
    });
    this.chipInfoGroup.valueChanges.subscribe(data => {
      this.isLoading = false;
      this.candidateChips = []
      let value: string = data['chipValue'];
      if (value.length > 1) {
        this.isLoading = true;
        this.service.getCandidates(this.typePerk, value).then(candidates => {
          for (let indexCandidate in candidates) {
            this.candidateChips.push(candidates[indexCandidate]);
          }
          this.isLoading = false;
        }).catch()
      }
    })
  }

  addChip() {
    let chipValue: string = this.chipInfoGroup.value['chipValue'];
    let indexPerk: number = this.indexOf(chipValue);
    console.log(chipValue);
    console.log(indexPerk);
    if (indexPerk >= 0) {
      this.chipValues.push(this.candidateChips[indexPerk]);
    } else {
      //TODO the creation should be done in the profile-conf component
      this.service.create(this.typePerk, chipValue).then(data => {
        console.log(data);
        this.chipValues.push(data);
      }).catch();
    }
    this.chipInfoGroup.value['chipValue'] = '';
  }

  removeChip(chip: Perk) {
    const index = this.chipValues.indexOf(chip);
    if (index >= 0) {
      this.chipValues.splice(index, 1);
    }
  }

  private indexOf(chip: string): number {
    for (let i in this.chipValues) {
      if (this.chipValues[i].name === chip) {
        return +i;
      }
    }
    return -1;
  }

  convertToString(perks: Perk[]): string[] {
    return Perk.convertToString(perks);
  }

}
