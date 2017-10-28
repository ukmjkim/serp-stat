import React, { Component } from "react"
import PropTypes from "prop-types"
import { connect } from "react-redux"
import { Route, Link } from 'react-router-dom';

import { NavLink } from "reactstrap";

class SiteSettings extends Component {
  constructor(props) {
    super(props);
  }

  componentDidMount() {
    if (this.props.activeSite) {
      console.log("SiteKeyword:componentDidMount");
      console.log(this.props);
    }
  }

  componentWillUpdate(nextProps, nextState) {
    if ((!this.props.activeSite && nextProps.activeSite) ||
        (this.props.activeSite != nextProps.activeSite)) {
        console.log("SiteKeyword:componentWillUpdate");
      console.log(nextProps);
    }
  }

  render() {
    const { site } = this.props.activSite;

    if (site === undefined || site == null) {
      return (
        <div>loading...</div>
      );
    }

    const mappedSite = `#/site/${site.id}/settings`;

    return (
      <NavLink href={mappedSite}>Settings</NavLink>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    activSite: state.sites.activeSite
  }
};

const mapDispatchToProps = (dispatch) => {
  return {
  }
};

export default connect(mapStateToProps, mapDispatchToProps)(SiteSettings);
