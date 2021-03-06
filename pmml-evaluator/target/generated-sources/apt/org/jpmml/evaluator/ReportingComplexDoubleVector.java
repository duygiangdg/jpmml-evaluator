/*
 * Copyright (c) 2017 Villu Ruusmann
 *
 * This file is part of JPMML-Evaluator
 *
 * JPMML-Evaluator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JPMML-Evaluator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with JPMML-Evaluator.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jpmml.evaluator;


public abstract class ReportingComplexDoubleVector
    extends ComplexDoubleVector
{

    private String expression = "";

    public ReportingComplexDoubleVector(int capacity) {
        super(capacity);
    }

    public ReportingComplexDoubleVector(int capacity, String expression) {
        super(capacity);
        if (expression!= null) {
            report(expression);
        }
    }

    protected abstract Report newReport();

    @Override
    public ReportingComplexDoubleVector add(double value) {
        ReportingComplexDoubleVector result = ((ReportingComplexDoubleVector) super.add(value));
        report(new StringBuilder(256).append(getExpression()).append("<cn>").append(value).append("</cn>").toString());
        return result;
    }

    @Override
    public ReportingComplexDoubleVector add(Number value) {
        ReportingComplexDoubleVector result = ((ReportingComplexDoubleVector) super.add(value));
        report(new StringBuilder(256).append(getExpression()).append("<cn>").append(value.doubleValue()).append("</cn>").toString());
        return result;
    }

    @Override
    public ReportingComplexDoubleVector add(double coefficient, Number factor) {
        ReportingComplexDoubleVector result = ((ReportingComplexDoubleVector) super.add(coefficient, factor));
        report(new StringBuilder(256).append(getExpression()).append("<apply><times/>").append("<cn>").append(coefficient).append("</cn>").append("<cn>").append(factor.doubleValue()).append("</cn>").append("</apply>").toString());
        return result;
    }

    @Override
    public ReportingDoubleValue get(int index) {
        return new ReportingDoubleValue((doubleValue(index)), newReport());
    }

    @Override
    public ReportingDoubleValue max() {
        return new ReportingDoubleValue(doubleMax(), newReport(), new StringBuilder(256).append("<apply><max/>").append(getExpression()).append("</apply>").toString());
    }

    @Override
    public ReportingDoubleValue median() {
        return new ReportingDoubleValue(doubleMedian(), newReport(), new StringBuilder(256).append("<apply><median/>").append(getExpression()).append("</apply>").toString());
    }

    @Override
    public ReportingDoubleValue sum() {
        return new ReportingDoubleValue(doubleSum(), newReport(), new StringBuilder(256).append("<apply><plus/>").append(getExpression()).append("</apply>").toString());
    }

    private void report(String expression) {
        setExpression(expression);
    }

    public String getExpression() {
        return this.expression;
    }

    private void setExpression(String expression) {
        this.expression = expression;
    }

}
