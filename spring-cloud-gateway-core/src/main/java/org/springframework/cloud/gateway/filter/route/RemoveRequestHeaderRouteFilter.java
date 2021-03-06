/*
 * Copyright 2013-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.springframework.cloud.gateway.filter.route;

import org.springframework.web.server.WebFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @author Spencer Gibb
 */
public class RemoveRequestHeaderRouteFilter implements RouteFilter {

	private static final String FAKE_HEADER = "_______force_______";

	@Override
	public WebFilter apply(String... args) {
		validate(1, args);
		final String header = args[0];

		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest().mutate()
					.header(FAKE_HEADER, "mutable") //TODO: is there a better way?
					.build();

			request.getHeaders().remove(FAKE_HEADER);
			request.getHeaders().remove(header);

			return chain.filter(exchange.mutate().request(request).build());
		};
	}
}
