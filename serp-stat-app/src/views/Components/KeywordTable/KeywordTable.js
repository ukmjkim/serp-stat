import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import {
    Badge,
    Row,
    Col,
    Card,
    CardHeader,
    CardBody,
    Table,
    Pagination,
    PaginationItem,
    PaginationLink
  } from 'reactstrap';

import { fetchPaginatedKeywords } from "../../../actions/keywords"
import { resetPaginatedKeywords } from "../../../actions/keywords"

import { KEYWORD_TABLE_PAGE_SIZE } from "../../../commons/const"
import { KEYWORD_TABLE_PAGINATION_SIZE } from "../../../commons/const"
import { currentPage } from "../../../commons/routes"


// https://github.com/AdeleD/react-paginate/blob/master/react_components/PaginationBoxView.js#L64
// How to use Pagination component
// https://www.youtube.com/watch?v=2qxNVzmiR8Y
class KeywordTable extends Component {
    constructor(props) {
      super(props);
    }

    componentDidMount() {
      const page = currentPage(this.props.location);
      const offset = (page-1) * KEYWORD_TABLE_PAGE_SIZE;
      if (this.props.activeSite) {
        this.props.fetchPaginatedKeywords(this.props.activeSite.site.id, offset, KEYWORD_TABLE_PAGE_SIZE);
      }
    }

    handlePageSelected(page) {
      const offset = (page-1) * KEYWORD_TABLE_PAGE_SIZE;
      this.props.fetchPaginatedKeywords(this.props.activeSite.site.id, offset, KEYWORD_TABLE_PAGE_SIZE);
    }

    render() {
      const { site } = this.props.activeSite;
      if (!site) {
        return (
          <div>Loading...</div>
        )
      }
      const { keywords } = this.props.keywordsPaginated;
      if (!keywords) {
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
      const keywordListMarkup = (keyword, index) => {
        const badgeColor = keyword.tracking ? "success" : "secondary";
        const tracking = keyword.tracking ? "Active" : "Inactive";
        const createdAtTimeStamp = Math.round(parseInt(keyword.createdAt));
        const createAt = (new Date(createdAtTimeStamp)).toUTCString();

        return (
          <tr key={index}>
          <td>{keyword.keyword}</td>
          <td>{createAt}</td>
          <td>{keyword.device.name}</td>
          <td>{`${keyword.market.region}_${keyword.market.lang}`}</td>
          <td>
              <Badge color={badgeColor}>{tracking}</Badge>
          </td>
          </tr>
        )
      };

      const keywordList = (keywords) => {
        return keywords.map( (keyword, index) => keywordListMarkup(keyword, index) );
      };


      const makePaginationMap = () => {
        const { site } = this.props.activeSite;
        const { totalCount } = this.props.keywordsCount.count;
        const page = currentPage(this.props.location);

        const pageMap = new Map();
        if (page > 1) {
          const prevPage = ((page-KEYWORD_TABLE_PAGINATION_SIZE) > 1) ? page-KEYWORD_TABLE_PAGINATION_SIZE : 1;
          let pageItem = {
            page: "Prev",
            link: `#/site/${site.id}/keyword?page=${prevPage}`,
            type: "previous"
          };
          pageMap.set(prevPage, pageItem)
        }

        const lastPage = ((totalCount % KEYWORD_TABLE_PAGE_SIZE) > 0)
              ? (Math.round(totalCount / KEYWORD_TABLE_PAGE_SIZE))
              : (Math.round(totalCount / KEYWORD_TABLE_PAGE_SIZE))-1;
        const maxPage = ((page+KEYWORD_TABLE_PAGINATION_SIZE) > lastPage)
              ? lastPage
              : (page+KEYWORD_TABLE_PAGINATION_SIZE);

        for (let i = page; i < maxPage; i++) {
          let pageItem = {
            page: i,
            link: `#/site/${site.id}/keyword?page=${i}`,
            type: "page"
          };
          pageMap.set(i, pageItem);
        }
        if (maxPage < lastPage) {
          const nextPage = maxPage;
          let pageItem = {
            page: "Next",
            link: `#/site/${site.id}/keyword?page=${nextPage}`,
            type: "next"
          };
          pageMap.set(nextPage, pageItem)
        }

        return pageMap;
      }

      const paginationListMarkup = (key, value) => {
        return (
            <PaginationItem key={key}>
              <PaginationLink onClick={this.handlePageSelected.bind(this, value.page)} href={value.link}>{value.page}</PaginationLink>
            </PaginationItem>
        )
      };

      const paginationList = () => {
        const paginationMap = makePaginationMap();
        return Array.from(paginationMap).map( ([key, value]) => paginationListMarkup(key, value) );
      };

      return (
        <div>
          <Table hover bordered striped responsive size="sm">
            <thead>
              <tr>
              <th>Keyword</th>
              <th>Date registered</th>
              <th>Device</th>
              <th>Market</th>
              <th>Status</th>
              </tr>
            </thead>
            <tbody>
              {keywordList(keywords)}
            </tbody>
          </Table>
          <nav>
            <Pagination>
            {paginationList()}
            </Pagination>
          </nav>
        </div>
      )
    }
}

KeywordTable.propTypes = {
    fetchPaginatedKeywords: PropTypes.func.isRequired,
    resetPaginatedKeywords: PropTypes.func.isRequired,
    activeSite: PropTypes.object.isRequired,
    keywordsPaginated: PropTypes.object.isRequired,
    keywordsCount: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => {
  return {
    activeSite: state.sites.activeSite,
    keywordsPaginated: state.keywords.keywordsPaginated,
    keywordsCount: state.keywords.keywordsCount,
  }
};

const mapDispatchToProps = (dispatch) => {
  return {
    fetchPaginatedKeywords: (siteId, offset, size) => dispatch(fetchPaginatedKeywords(siteId, offset, size)),
    resetPaginatedKeywords: () => dispatch(resetPaginatedKeywords()),
  }
};

export default connect(mapStateToProps, mapDispatchToProps)(KeywordTable);
