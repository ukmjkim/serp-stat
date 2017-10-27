import React, { Component } from "react"
import PropTypes from "prop-types"
import { connect } from "react-redux"
import { Route, Link } from 'react-router-dom';

import { NavLink } from "reactstrap";

import { sitesSelected } from "../../../actions/sites"

class SiteSettings extends Component {
  constructor(props) {
    super(props);
  }

  componentDidMount() {
    if (this.props.sitesSelected) {
      console.log("SiteKeyword:componentDidMount");
      console.log(this.props);
    }
  }

  componentWillUpdate(nextProps, nextState) {
    if ((!this.props.sitesSelected && nextProps.sitesSelected) ||
        (this.props.sitesSelected != nextProps.sitesSelected)) {
        console.log("SiteKeyword:componentWillUpdate");
      console.log(nextProps);
    }
  }

  render() {
    if (this.props.sitesSelected === null || this.props.sitesSelected == undefined) {
      return (
        <div>loading...</div>
      );
    }

    const { sitesSelected } = this.props;
    const mappedSite = "#/site/"+sitesSelected.id+"/settings";

    return (
      <NavLink href={mappedSite}>Settings</NavLink>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    sitesSelected: state.sitesSelected,
  }
};

const mapDispatchToProps = (dispatch) => {
  return {
  }
};

export default connect(mapStateToProps, mapDispatchToProps)(SiteSettings);
