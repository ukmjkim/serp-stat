import React, { Component } from "react"
import { connect } from "react-redux"
import {bindActionCreators} from 'redux';

import { Dropdown, DropdownToggle, DropdownMenu, DropdownItem } from "reactstrap";

import { fetchUser } from "../../../actions/userActions"
import { fetchSites } from "../../../actions/sitesActions"

class SiteDropdown extends Component {
  constructor(props) {
    super(props);

    console.log(props)

    this.toggle = this.toggle.bind(this);
    this.select = this.select.bind(this);

    this.state = {
      dropdownOpen: false,
      value : "Home",
    };
  }

  componentWillMount() {
    console.log(this.props)
    this.props.fetchUser();
  }

  toggle() {
    this.setState({
      dropdownOpen: !this.state.dropdownOpen
    });
  }

  select(e) {
    console.log("e.target.value: " + e.target.value);
    const siteId = e.target.value;
    this.setState({
      dropdownOpen: !this.state.dropdownOpen,
      value: siteId
    });
  }
  render() {
    const { user, sites } = this.props;
console.log("user");
console.log(user);

    if (!sites) {
      return (
        <Dropdown isOpen={this.state.dropdownOpen} toggle={this.toggle}>
          <DropdownToggle caret>
            Site List
          </DropdownToggle>
        </Dropdown>
      );
    }

    const mappedSites = sites.map(site => <DropdownItem key={site.id}  onClick={this.select.bind(this)}>{site.title}</DropdownItem>)
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

const mapStateToProps = (state, ownProps) => ({
    user: state.user.user,
    userFetched: state.user.fetched,
    sites: state.user.sites,
});

const mapDispatchToProps = (dispatch) => ({
  fetchUser: bindActionCreators(fetchUser, dispatch),
  fetchSites: bindActionCreators(fetchSites, dispatch)
});

export default connect(mapStateToProps, mapDispatchToProps)(SiteDropdown);
