export const routes = {
  '/': 'Home',
  '/dashboard': 'Dashboard',
  '/site/[0-9]$': 'Site Dashboard',
  '/site/[0-9]/tag/[0-9]$': 'Tag Dashboard',
  '/keyword': 'Keyword',
  '/settings': 'Settings',
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
