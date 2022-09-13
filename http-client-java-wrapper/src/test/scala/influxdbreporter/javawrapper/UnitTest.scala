/*
 * Copyright 2015
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
package influxdbreporter.javawrapper;

import influxdbreporter.ConnectionData
import influxdbreporter.core.{MetricClient, MetricClientFactory}
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatest.wordspec.AnyWordSpec

import java.util.concurrent.TimeUnit

class UnitTest extends AnyWordSpec {

  "InfluxdbReporter name should be set to TestInfluxdbReporter" in {
    val registry = new MetricRegistry("test-registry")
    val a: influxdbreporter.core.InfluxdbReporter[String] = createTestReporter(registry).reporter
    a.getName shouldBe Some("TestInfluxdbReporter")
  }

  def createTestReporter(registry: MetricRegistry): InfluxdbReporter = {
    val metricClientFactory: MetricClientFactory[String] = new MetricClientFactory[String]() {
      override def create(): MetricClient[String] = {
        HttpInfluxdbClient.defaultHttpClient(
          ConnectionData("addr", 2000, "db", "user", "pass")
        );
      }
    }
    new influxdbreporter.javawrapper.InfluxdbReporter(registry, metricClientFactory, 10, TimeUnit.SECONDS, 0, "TestInfluxdbReporter");
  }
}
