/*
 * Copyright 2015 Rod MacKenzie
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
package com.github.rodm.teamcity.tasks

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class StartAgent extends TeamCityTask {

    @Input
    String agentOptions

    StartAgent() {
        description = 'Starts the TeamCity Agent'
    }

    @TaskAction
    public void start() {
        validate()
        def name = isWindows() ? 'agent.bat' : 'agent.sh'
        project.ant.exec(executable: "$homeDir/buildAgent/bin/$name", spawn: true) {
            env key: 'JAVA_HOME', path: getJavaHome()
            env key: 'TEAMCITY_AGENT_OPTS', value: getAgentOptions()
            arg value: 'start'
        }
    }
}
