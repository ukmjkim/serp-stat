import queryString from 'query-string';

export const routes = {
  '/': 'Home',
  '/dashboard': 'Dashboard',
  '/site/[0-9]$': 'Site Dashboard',
  '/site/[0-9]/tag/[0-9]$': 'Tag Dashboard',
  '/site/[0-9]/keyword': 'Keyword',
  '/site/[0-9]/settings': 'Settings',
};

export const findRouteInfo = (path) => {
	for (var routePath in routes) {
 		const routeName = routes[routePath];
  	if (path == routePath) {
    	return {path: path, routePath: routePath, routeName: routeName};
    } else {
      if (routePath != "/") {
				if (path.search(routePath) == 0) {
					return {
          	path: path,
            routePath: routePath,
           	routeName: routeName
          };
        }
      }
    }
  };
  return null;
}

export const getPaths = (pathname) => {
  const paths = [];
  const route = findRouteInfo("/");
  if (route != null) {
    paths.push(route);
  }
  if (pathname === '/') return paths;

  pathname.split('/').reduce((prev, curr, index) => {
    const currPath = `${prev}/${curr}`;
    const route = findRouteInfo(currPath);
    if (route != null) {
      paths.push(route);
    }
    return currPath;
  });

  return paths;
};

export const currentPage = (location) => {
  const { search } = location;
  const queryParameters = queryString.parse(search);
  if ((queryParameters) && (queryParameters.page)) {
    return parseInt(queryParameters.page);
  } else {
    return 1;
  }
}
