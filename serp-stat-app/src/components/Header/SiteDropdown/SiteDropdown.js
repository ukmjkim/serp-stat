import React, { Component } from "react"
import PropTypes from "prop-types"
import { connect } from "react-redux"
import { Route, Link } from 'react-router-dom';

import { Dropdown, DropdownToggle, DropdownMenu, DropdownItem } from "reactstrap";

import { sitesFetchData } from "../../../actions/sites"
import { sitesSelected } from "../../../actions/sites"

// https://reacttraining.com/react-router/web/example/modal-gallery
class SiteDropdown extends Component {
  constructor(props) {
    super(props);

    this.toggle = this.toggle.bind(this);
    this.select = this.select.bind(this);

    this.state = {
      dropdownOpen: false,
      value : "Home"
    };
  }

  componentDidMount() {
    this.props.fetchData(1);
  }

  toggle() {
    this.setState({
      dropdownOpen: !this.state.dropdownOpen
    });
  }

  select(e, site) {
    this.setState({
      dropdownOpen: !this.state.dropdownOpen,
      redirect: true
    });
    this.props.selectSite(site);
  }
  render() {
    if (this.props.sites === undefined || this.props.sites.length == 0) {
      return (
        <Dropdown isOpen={this.state.dropdownOpen} toggle={this.toggle}>
          <DropdownToggle caret>
            Site List
          </DropdownToggle>
        </Dropdown>
      );
    }

    const { sites } = this.props;

    const mappedSites = sites.map(site =>
      <DropdownItem key={site.id}  onClick={ (e) => this.select(e, site) }>
        <Link to={site.link}>{site.title}</Link>
      </DropdownItem>
    )
    return (
      <Dropdown isOpen={this.state.dropdownOpen} toggle={this.toggle}>
        <DropdownToggle caret>
          Site List
        </DropdownToggle>
        <DropdownMenu>
          {mappedSites}
        </DropdownMenu>
      </Dropdown>
    );
  }
}

SiteDropdown.propTypes = {
    fetchData: PropTypes.func.isRequired,
    selectSite: PropTypes.func.isRequired,
    sites: PropTypes.array.isRequired,
    hasErrored: PropTypes.bool.isRequired,
    isLoading: PropTypes.bool.isRequired
};

const mapStateToProps = (state) => {
  return {
    sitesSelected: state.sitesSelected,
    sites: state.sites,
    hasErrored: state.sitesHasErrored,
    isLoading: state.sitesIsLoading
  }
};

const mapDispatchToProps = (dispatch) => {
  return {
    fetchData: (id) => dispatch(sitesFetchData(id)),
    selectSite: (site) => dispatch(sitesSelected(site))
  }
};

export default connect(mapStateToProps, mapDispatchToProps)(SiteDropdown);
