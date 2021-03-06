/*
 * Copyright 2020 Wuyi Chen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ftgo.accountingservice.configuration.model;

/**
 * The configuration parameters class for the channel configuration.
 * 
 * @author  Wuyi Chen
 * @date    05/14/2020
 * @version 1.0
 * @since   1.0
 */
public class AccountingServiceChannelConfiguration {
	private String commandDispatcherId;
	private String commandChannel;

	public AccountingServiceChannelConfiguration(String commandDispatcherId, String commandChannel) {
		this.commandDispatcherId = commandDispatcherId;
		this.commandChannel      = commandChannel;
	}

	public String getCommandDispatcherId() { return commandDispatcherId; }
	public String getCommandChannel()      { return commandChannel;      }
}
