import React, { Component } from 'react';
import PropTypes from "prop-types"
import { connect } from "react-redux"

import { fetchKeywordsCount } from "../../../actions/keywords"

class KeywordTableHead extends Component {
  constructor(props) {
    super(props);
  }

  componentDidMount() {
    if (this.props.activeSite) {
      this.props.fetchKeywordsCount(this.props.activeSite.site.id);
    }
  }

    render() {
      const { site } = this.props.activeSite;
      if (!site) {
        return (
          <div>Loading...</div>
        )
      }
      const { count } = this.props.keywordsCount;
      if (!count) {
        return (
          <div>Loading...</div>
        )
      }
      return (
        <div>
          <i className="fa fa-align-justify"></i> All Keywords in <strong>{site.title}</strong> - Total {count.totalCount} Keywords.
        </div>
      )
    }
}

KeywordTableHead.propTypes = {
  fetchKeywordsCount: PropTypes.func.isRequired,
  activeSite: PropTypes.object.isRequired,
  keywordsCount: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => {
  return {
    activeSite: state.sites.activeSite,
    keywordsCount: state.keywords.keywordsCount
  }
};

const mapDispatchToProps = (dispatch) => {
  return {
    fetchKeywordsCount: (siteId) => dispatch(fetchKeywordsCount(siteId)),
  }
};

export default connect(mapStateToProps, mapDispatchToProps)(KeywordTableHead);
