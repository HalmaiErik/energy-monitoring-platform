import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ChartDataSets, ChartType } from 'chart.js';
import { BaseChartDirective, Label } from 'ng2-charts';
import { SensorLog } from '../model/sensorLog';
import { SensorlogService } from '../service/sensorlog.service';

@Component({
  selector: 'app-sensor',
  templateUrl: './sensor.component.html',
  styleUrls: ['./sensor.component.css']
})
export class SensorComponent implements OnInit {

  public sensorId: number;
  public logs: SensorLog[];
  public shownLogs: SensorLog[];
  public filterDate: string;

  @ViewChild(BaseChartDirective) chart: BaseChartDirective;
  public chartData: ChartDataSets[];
  public chartLabels: Label[];
  public chartType = 'bar';
  public chartOptions = {
    responsive: true,
    scales: {
      yAxes: [{
          ticks: {
              beginAtZero: true
          }
      }]
  }
  };
  public chartReady: boolean = false;

  constructor(private sensorLogService: SensorlogService, private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params.subscribe(
      params => {
        this.sensorId = params['sensor'];
      }
    );
    this.getLogs();
  }

  private getLogs(): void {
    this.sensorLogService.getLogsOfDevice(this.sensorId).subscribe(
      (resp: SensorLog[]) => {
        this.logs = resp;
        this.shownLogs = this.logs;
        this.getChartDataSetAndLabels();
      }
    );
  }

  public onAddLog(addForm: NgForm): void {
    document.getElementById('add-log-form')!.click();
    let s: SensorLog = addForm.value;
    console.log(s);
    this.sensorLogService.addSensorLog(s).subscribe(
      (response: Number) => {
        console.log(response);
        this.getLogs();
        addForm.reset();
      },
    );
  }

  public onOpenModal(log: SensorLog, mode: string, event: Event): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addLogModal');
    }
    container!.appendChild(button);
    button.click();
  }

  getChartDataSetAndLabels() {
    this.chartData = [];
    this.chartLabels = [];

    let dataVar : number[] = [];
    let labelVar: string[] = [];
    for (let i of Object.keys(this.shownLogs)) {
      var log = this.shownLogs[i];
      dataVar.push(log.energyConsumption);
      labelVar.push(log.timeStamp);
    }
    this.chartData = [{ data: dataVar, label: 'Energy Consumption' }]
    this.chartLabels = labelVar;

    console.log(this.chartData);
    console.log(this.chartLabels);
    this.chartReady = true;
    setTimeout(() => {
      if (this.chart && this.chart.chart && this.chart.chart.config) {
        this.chart.chart.update();
      }
    })
  }

  filter() {
    this.shownLogs = [];
    for (let log of this.logs) {
      if (log.timeStamp.includes(this.filterDate)) {
        this.shownLogs.push(log);
      }
    }
    this.getChartDataSetAndLabels();
  }

  filterReset() {
    this.shownLogs = this.logs;
    this.getChartDataSetAndLabels();
  }
}
