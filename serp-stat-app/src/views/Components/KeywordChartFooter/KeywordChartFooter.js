import React, { Component } from 'react';
import {Bar, Line} from 'react-chartjs-2';
import {
    Progress
} from 'reactstrap';

export default class KeywordChartFooter extends Component {
    constructor(props) {
      super(props);
    }

    render() {

      return (
        <ul>
          <li>
            <div className="text-muted">Visits</div>
            <strong>29.703 Users (40%)</strong>
            <Progress className="progress-xs mt-2" color="success" value="40"/>
          </li>
          <li className="d-none d-md-table-cell">
            <div className="text-muted">Unique</div>
            <strong>24.093 Users (20%)</strong>
            <Progress className="progress-xs mt-2" color="info" value="20"/>
          </li>
          <li>
            <div className="text-muted">Pageviews</div>
            <strong>78.706 Views (60%)</strong>
            <Progress className="progress-xs mt-2" color="warning" value="60"/>
          </li>
          <li className="d-none d-md-table-cell">
            <div className="text-muted">New Users</div>
            <strong>22.123 Users (80%)</strong>
            <Progress className="progress-xs mt-2" color="danger" value="80"/>
          </li>
          <li className="d-none d-md-table-cell">
            <div className="text-muted">Bounce Rate</div>
            <strong>Average 40.15%</strong>
            <Progress className="progress-xs mt-2" color="primary" value="40"/>
          </li>
        </ul>
      )
    }
}
