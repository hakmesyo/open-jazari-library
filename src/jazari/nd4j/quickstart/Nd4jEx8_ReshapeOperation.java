/* *****************************************************************************
 *
 *
 *
 * This program and the accompanying materials are made available under the
 * terms of the Apache License, Version 2.0 which is available at
 * https://www.apache.org/licenses/LICENSE-2.0.
 *  See the NOTICE file distributed with this work for additional
 *  information regarding copyright ownership.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 ******************************************************************************/

package jazari.nd4j.quickstart;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

/**
 * --- Nd4j Example 8: Reshape Operation ---
 *
 * In this example, we'll see ways to reshape a matrix
 *
 * Created by cvn on 9/7/14.
 */
public class Nd4jEx8_ReshapeOperation {

    public static void main(String[] args) {

        INDArray nd = Nd4j.create(new float[]{1, 2, 3, 4}, 2, 2);
        INDArray ndv;

        System.out.println(nd);

        ndv = nd.transpose(); // the two and the three switch - a simple transpose
        System.out.println(ndv);

        INDArray nd2 = Nd4j.create(new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}, 2, 6);
        System.out.println(nd2);

        ndv = nd2.transpose(); // this will make a long-rowed matrix into a tall-columned matrix
        System.out.println(ndv);

        ndv = nd2.reshape(3,4); // reshape allows you to enter new row and column parameters, as long as the product
        System.out.println(ndv); // of the new rows and columns equals the product of the old; e.g. 2 * 6 = 3 * 4

        ndv = nd2.transpose(); // one more transpose just for fun.
        System.out.println(ndv);

        nd2 = Nd4j.create(new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}); //now we want a linear matrix, i.e. a row vector

        ndv = nd2.broadcast(6,12); // broadcast takes a row vector and adds it to all the rows
        System.out.println(ndv);


    }
}
