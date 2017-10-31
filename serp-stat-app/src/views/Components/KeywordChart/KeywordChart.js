import React, { Component } from 'react';
import {Bar, Line} from 'react-chartjs-2';
import {
    Badge,
    Button,
    ButtonToolbar,
    ButtonGroup,
    Row,
    Col,
    Card,
    CardHeader,
    CardTitle,
    CardBody,
    CardFooter,
    Label,
    Input,
    Progress
} from 'reactstrap';

const brandPrimary = '#20a8d8';
const brandSuccess = '#4dbd74';
const brandInfo = '#63c2de';
const brandWarning = '#f8cb00';
const brandDanger = '#f86c6b';


// convert Hex to RGBA
function convertHex(hex, opacity) {
    hex = hex.replace('#', '');
    var r = parseInt(hex.substring(0, 2), 16);
    var g = parseInt(hex.substring(2, 4), 16);
    var b = parseInt(hex.substring(4, 6), 16);

    var result = 'rgba(' + r + ',' + g + ',' + b + ',' + opacity / 100 + ')';
    return result;
}

//Random Numbers
function random(min, max) {
    return Math.floor(Math.random() * (max - min + 1) + min);
}

var elements = 27;
var data1 = [];
var data2 = [];
var data3 = [];

for (var i = 0; i <= elements; i++) {
  data1.push(random(50, 200));
  data2.push(random(80, 100));
  data3.push(65);
}

const mainChart = {
    labels: ['M', 'T', 'W', 'T', 'F', 'S', 'S', 'M', 'T', 'W', 'T', 'F', 'S', 'S', 'M', 'T', 'W', 'T', 'F', 'S', 'S', 'M', 'T', 'W', 'T', 'F', 'S', 'S'],
    datasets: [
        {
        label: 'My First dataset',
        backgroundColor: convertHex(brandInfo, 10),
        borderColor: brandInfo,
        pointHoverBackgroundColor: '#fff',
        borderWidth: 2,
        data: data1
        },
        {
        label: 'My Second dataset',
        backgroundColor: 'transparent',
        borderColor: brandSuccess,
        pointHoverBackgroundColor: '#fff',
        borderWidth: 2,
        data: data2
        },
        {
        label: 'My Third dataset',
        backgroundColor: 'transparent',
        borderColor: brandDanger,
        pointHoverBackgroundColor: '#fff',
        borderWidth: 1,
        borderDash: [8, 5],
        data: data3
        }
    ]
}

const mainChartOpts = {
    maintainAspectRatio: false,
    legend: {
        display: false
    },
    scales: {
        xAxes: [{
        gridLines: {
            drawOnChartArea: false,
        }
        }],
        yAxes: [{
        ticks: {
            beginAtZero: true,
            maxTicksLimit: 5,
            stepSize: Math.ceil(250 / 5),
            max: 250
        }
        }]
    },
    elements: {
        point: {
        radius: 0,
        hitRadius: 10,
        hoverRadius: 4,
        hoverBorderWidth: 3,
        }
    }
}


export default class KeywordChart extends Component {
    constructor(props) {
      super(props);
    }

    render() {

      return (
        <div className="animated fadeIn">
          <Row>
            <Col sm="5">
              <CardTitle className="mb-0">Traffic</CardTitle>
              <div className="small text-muted">November 2015</div>
            </Col>
            <Col sm="7" className="d-none d-sm-inline-block">
              <Button color="primary" className="float-right"><i className="icon-cloud-download"></i></Button>
              <ButtonToolbar className="float-right" aria-label="Toolbar with button groups">
                <ButtonGroup className="mr-3" data-toggle="buttons" aria-label="First group">
                  <Label htmlFor="option1" className="btn btn-outline-secondary">
                    <Input type="radio" name="options" id="option1"/> Day
                  </Label>
                  <Label htmlFor="option2" className="btn btn-outline-secondary active">
                    <Input type="radio" name="options" id="option2" defaultChecked/> Month
                  </Label>
                  <Label htmlFor="option3" className="btn btn-outline-secondary">
                    <Input type="radio" name="options" id="option3"/> Year
                  </Label>
                </ButtonGroup>
              </ButtonToolbar>
            </Col>
          </Row>
          <div className="chart-wrapper" style={{height: 300 + 'px', marginTop: 40 + 'px'}}>
            <Line data={mainChart} options={mainChartOpts} height={300}/>
          </div>
        </div>
      )
    }
}
