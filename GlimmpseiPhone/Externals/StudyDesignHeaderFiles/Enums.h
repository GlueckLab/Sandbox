/*
 * Communication Domain layer for iPhone applications which
 * interacts with Glimmpse Software Subsystems.
 *
 * Copyright (C) 2010 Regents of the University of Colorado.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA  02110-1301, USA.
 */

#import <Foundation/Foundation.h>

/**
 * Enum objects referred in the domain layer.
 *
 * @author Aarti Munjal
 */

@interface Enums : NSObject


/**
 * Enum object for covariance matrix types
 *
 */

typedef enum CovarianceTypeEnum {
    /** Lear correlation */
    LEAR_CORRELATION,
    /** Unstructured correlation. */
    UNSTRUCTURED_CORRELATION,
    /** Unstructured covariance. */
    UNSTRUCTURED_COVARIANCE
} CovarianceTypeEnum;

/**
 * Enum object referred in 'PowerCurveDescription' class.
*/

typedef enum HorizontalAxisLabelEnum {
    /** The VARIABILITY SCALE FACTOR. */
    VARIABILITY_SCALE_FACTOR,
    /** The TOTAL SAMPLE SIZE. */
    TOTAL_SAMPLE_SIZE,
    /** The REGRESSION COEEFICIENT SCALE FACTOR. */
    REGRESSION_COEEFICIENT_SCALE_FACTOR
} HorizontalAxisLabelEnum;


/**
 * Enum of available types of trends which can be tested in a hypothesis.
 */

typedef enum HypothesisTrendTypeEnum {
    
    /** The NONE. */
    NONE,
    /** The CHANG e_ fro m_ baseline. */
    CHANGE_FROM_BASELINE,
    /** The AL l_ poynomial. */
    ALL_POLYNOMIAL,
    /** The LINEAR. */
    LINEAR,
    /** The QUADRATIC. */
    QUADRATIC,
    /** The CUBIC. */
    CUBIC
}HypothesisTrendTypeEnum;


/**
 * Enum object referred in 'Hypothesis' class.
 */

typedef enum HypothesisTypeEnum {
    /** Grand Mean hypothesis. */
    GRAND_MEAN,
    /** The MAIN EFFECT. */
    MAIN_EFFECT,
    /** The INTERACTION. */
    INTERACTION,
    /** The TREND. */
    TREND
} HypothesisTypeEnum;


/**
 * Enum object referred in 'PowerMethod' and
 * 'PowerCurveDescription'class.
 */

typedef enum PowerMethodEnum {
    /** The CONDITIONAL. */
    CONDITIONAL,
    /** The UNCONDITIONAL. */
    UNCONDITIONAL,
    
    /** The QUANTILE. */
    QUANTILE
} PowerMethodEnum;


/**
 * Enum of types of repeated measures.
 */


typedef enum RepeatedMeasuresDimensionType {
    
    /** The NUMERICAL. */
    NUMERICAL,
    
    /** The ORDINAL. */
    ORDINAL,
    
    /** The CATEGORICAL. */
    CATEGORICAL
}  RepeatedMeasuresDimensionType;


/**
 * Enum object referred in 'StudyDesign' class.
 */

typedef enum SolutionTypeEnum {
    
    /** The POWER. */
    POWER,
    
    /** The SAMPLE SIZE. */
    SAMPLE_SIZE,
    
    /** The DETECTABLE DIFFERENCE. */
    DETECTABLE_DIFFERENCE

} SolutionTypeEnum;

/**
 * Enum object referred in 'StatisticalTest' and
 * 'PowerCurveDescription'class.
 */


typedef enum StatisticalTestTypeEnum {
    
    /** The UNIREP. */
    UNIREP,
    
    /** The UNIREPBOX. */
    UNIREPBOX,
    
    /** The UNIREPGG. */
    UNIREPGG,
    
    /** The UNIREPHF. */
    UNIREPHF,
    
    /** The WL. */
    WL,
    
    /** The PBT. */
    PBT,
    
    /** The HLT. */
    HLT
    
}  StatisticalTestTypeEnum;


/**
 * Enum object referred in 'PowerCurveDescription' class.
 *
 */


typedef enum StratificationVariableEnum {
    
    /** The TYP e_ i_ error. */
    TYPE_I_ERROR,
    
    /** The STATISTICA l_ test. */
    STATISTICAL_TEST
    
} StratificationVariableEnum;
    

/**
 * Enum object referred in 'StudyDesign' class.
 *
 */


typedef enum StudyDesignViewTypeEnum {
    
    /** The GUIDE d_ mode. */
    GUIDED_MODE,
    
    /** The MATRI x_ mode. */
    MATRIX_MODE,
    
    /** The UPLOAD. */
    UPLOAD

} StudyDesignViewTypeEnum;

@end
