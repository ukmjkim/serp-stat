import React, { Component } from 'react';

import KeywordTable from '../Components/KeywordTable';
import KeywordChart from '../Components/KeywordChart';

export default class Keyword extends Component {
    constructor(props) {
      super(props);
    }


    render() {

      return (
        <div className="animated fadeIn">
            <KeywordTable />
            <KeywordChart />
        </div>
      )
    }
}
